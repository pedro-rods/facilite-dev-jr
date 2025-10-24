import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAddress, NewAddress } from '../address.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAddress for edit and NewAddressFormGroupInput for create.
 */
type AddressFormGroupInput = IAddress | PartialWithRequiredKeyOf<NewAddress>;

type AddressFormDefaults = Pick<NewAddress, 'id'>;

type AddressFormGroupContent = {
  id: FormControl<IAddress['id'] | NewAddress['id']>;
  cep: FormControl<IAddress['cep']>;
  street: FormControl<IAddress['street']>;
  number: FormControl<IAddress['number']>;
  complement: FormControl<IAddress['complement']>;
  district: FormControl<IAddress['district']>;
  city: FormControl<IAddress['city']>;
  uf: FormControl<IAddress['uf']>;
};

export type AddressFormGroup = FormGroup<AddressFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AddressFormService {
  createAddressFormGroup(address: AddressFormGroupInput = { id: null }): AddressFormGroup {
    const addressRawValue = {
      ...this.getFormDefaults(),
      ...address,
    };
    return new FormGroup<AddressFormGroupContent>({
      id: new FormControl(
        { value: addressRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      cep: new FormControl(addressRawValue.cep, {
        validators: [Validators.required, Validators.minLength(8), Validators.maxLength(9)],
      }),
      street: new FormControl(addressRawValue.street, {
        validators: [Validators.required, Validators.maxLength(120)],
      }),
      number: new FormControl(addressRawValue.number, {
        validators: [Validators.maxLength(10)],
      }),
      complement: new FormControl(addressRawValue.complement, {
        validators: [Validators.maxLength(60)],
      }),
      district: new FormControl(addressRawValue.district, {
        validators: [Validators.maxLength(80)],
      }),
      city: new FormControl(addressRawValue.city, {
        validators: [Validators.required, Validators.maxLength(80)],
      }),
      uf: new FormControl(addressRawValue.uf, {
        validators: [Validators.required],
      }),
    });
  }

  getAddress(form: AddressFormGroup): IAddress | NewAddress {
    return form.getRawValue() as IAddress | NewAddress;
  }

  resetForm(form: AddressFormGroup, address: AddressFormGroupInput): void {
    const addressRawValue = { ...this.getFormDefaults(), ...address };
    form.reset(
      {
        ...addressRawValue,
        id: { value: addressRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AddressFormDefaults {
    return {
      id: null,
    };
  }
}
