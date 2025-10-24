import { Uf } from 'app/entities/enumerations/uf.model';

export interface IAddress {
  id: number;
  cep?: string | null;
  street?: string | null;
  number?: string | null;
  complement?: string | null;
  district?: string | null;
  city?: string | null;
  uf?: keyof typeof Uf | null;
}

export type NewAddress = Omit<IAddress, 'id'> & { id: null };
