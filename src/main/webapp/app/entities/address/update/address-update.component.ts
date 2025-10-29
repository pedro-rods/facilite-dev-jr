import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { Uf } from 'app/entities/enumerations/uf.model';
import { IAddress } from '../address.model';
import { AddressService, CepLookup } from '../service/address.service';
import { AddressFormGroup, AddressFormService } from './address-form.service';
import { distanceAndSkiddingToXY } from '@popperjs/core/lib/modifiers/offset';

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;
  address: IAddress | null = null;
  ufValues = Object.keys(Uf);
  private lastCepLookedUp: string | null = null;

  protected addressService = inject(AddressService);
  protected addressFormService = inject(AddressFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AddressFormGroup = this.addressFormService.createAddressFormGroup();

  cepLoading = false;
  cepErrorMsg: string | null = null;
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      this.address = address;
      if (address) {
        this.updateForm(address);
      }
    });
  }
  private parseUf(value: string | null | undefined): Uf | null { // helper pra parsear a uf retornada do backend
    const v = (value ?? '').toUpperCase().trim();
    return (Object.values(Uf) as string[]).includes(v) ? (v as Uf) : null;
  }
  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.addressFormService.getAddress(this.editForm);
    if (address.id !== null) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(address: IAddress): void {
    this.address = address;
    this.addressFormService.resetForm(this.editForm, address);
  }
  // mascara do campo cep 
  onCepInput(event: Event): void {
    const input = event.target as HTMLInputElement;
    const digits = input.value.replace(/\D/g, '').slice(0, 8);

    // aplica a máscara 00000-000 quando houver mais de 5 dígitos
    const masked =
      digits.length > 5 ? `${digits.slice(0, 5)}-${digits.slice(5)}` : digits;

    // evita loop de eventos e não dispara validações a cada keypress
    this.editForm.get('cep')?.setValue(masked, { emitEvent: false });
  }
  onBuscarCep(): void {
    this.cepErrorMsg = null;

    const cepCtrl = this.editForm.get('cep');
    const numberCtrl = this.editForm.get('number');

    const rawCep = (cepCtrl?.value ?? '').toString();
    const digits = rawCep.replace(/\D/g, '');
    if (this.lastCepLookedUp === digits) return;

    // validação simples: CEP precisa ter 8 dígitos
    if (digits.length !== 8) {
      this.cepErrorMsg = 'CEP inválido';
      // opcional: marcar erro de minlength para aproveitar mensagens já existentes
      cepCtrl?.setErrors({ minlength: true });
      return;
    }

    this.lastCepLookedUp = digits
    this.cepLoading = true;

    this.addressService.cepLookup(digits).pipe(finalize(() => (this.cepLoading = false))).subscribe({
      next: (res: CepLookup) => {
        // mantém o número se o usuário já digitou algo
        const currentNumber = (numberCtrl?.value ?? '').toString().trim();

        // atualiza demais campos do formulário
        this.editForm.patchValue({
          street: res.street ?? '',
          district: res.district ?? '',
          city: res.city ?? '',
          uf: this.parseUf(res.uf),
          complement: res.complement ?? '',
          // number: só setaremos abaixo se estiver vazio
        });

        if (!currentNumber) {
          // preenche number se não houver valor digitado
          const incoming = res.number == null ? '' : String(res.number);
          this.editForm.patchValue({ number: incoming });
        }

        // re-mascarar CEP no form (00000-000) sem disparar validações/eventos desnecessários
        cepCtrl?.setValue(digits.replace(/^(\d{5})(\d{3})$/, '$1-$2'), { emitEvent: false });
      },
      error: (err: HttpErrorResponse) => {
        console.error('Erro ao buscar CEP', err);
        // usa mensagem do backend, se vier; senão, faz fallback pelo status
        const backendMsg = (err?.error?.message || err?.message || '').trim();
        let msg = ''
        if (err.status === 400) msg = 'CEP inválido';
        else if (err.status === 404) msg = 'CEP não encontrado';
        else if (err.status === 502) msg = 'Serviço de CEP indisponível';
        else msg = 'Não foi possível buscar o CEP. Tente novamente.';

        this.cepErrorMsg = msg;
        this.lastCepLookedUp = null;
      },
    });
  }
  // dispara automaticamente a busca quando o campo CEP perde o foco 
  onCepBlur(): void {
    if (this.cepLoading) return;
    const cepCtrl = this.editForm.get('cep');
    const rawCep = (cepCtrl?.value ?? '').toString();
    const digits = rawCep.replace(/\D/g, '');  // verificação da qtd de digitos
    if (digits.length === 8) {
      this.onBuscarCep();
    } else if (digits.length > 0) {
      
      this.cepErrorMsg = 'CEP inválido';
      cepCtrl?.setErrors({ minlength: true });
    } else {
      // campo vazio: limpa estado
      this.cepErrorMsg = null;
      this.lastCepLookedUp = null;
      cepCtrl?.setErrors(null);
    }
  }

}

