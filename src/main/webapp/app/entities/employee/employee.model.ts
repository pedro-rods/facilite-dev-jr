import dayjs from 'dayjs/esm';
import { IAddress } from 'app/entities/address/address.model';
import { IDepartment } from 'app/entities/department/department.model';

export interface IEmployee {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phone?: string | null;
  hireDate?: dayjs.Dayjs | null;
  salary?: number | null;
  active?: boolean | null;
  address?: Pick<IAddress, 'id'> | null;
  department?: Pick<IDepartment, 'id' | 'name'> | null;
}

export type NewEmployee = Omit<IEmployee, 'id'> & { id: null };
