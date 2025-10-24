import dayjs from 'dayjs/esm';

import { IEmployee, NewEmployee } from './employee.model';

export const sampleWithRequiredData: IEmployee = {
  id: 8899,
  firstName: 'Gail',
  lastName: 'Kerluke',
  email: 'vZ@(h>yg}.K',
  hireDate: dayjs('2025-10-24T20:01'),
  salary: 469.91,
  active: false,
};

export const sampleWithPartialData: IEmployee = {
  id: 5984,
  firstName: 'Bridgette',
  lastName: 'Kutch',
  email: 'xv@ag3.P',
  phone: '1-604-712-3237 x2987',
  hireDate: dayjs('2025-10-24T08:01'),
  salary: 20622.33,
  active: true,
};

export const sampleWithFullData: IEmployee = {
  id: 19019,
  firstName: 'Osborne',
  lastName: 'Luettgen',
  email: 'Yz$eN@T.<E[F+',
  phone: '(901) 849-1627',
  hireDate: dayjs('2025-10-24T15:06'),
  salary: 32557.99,
  active: false,
};

export const sampleWithNewData: NewEmployee = {
  firstName: 'Camryn',
  lastName: 'Padberg',
  email: 'K@[.fLw/+',
  hireDate: dayjs('2025-10-24T18:48'),
  salary: 6438.44,
  active: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
