import { IAddress, NewAddress } from './address.model';

export const sampleWithRequiredData: IAddress = {
  id: 2568,
  cep: 'admired y',
  street: '5th Street',
  city: 'Loraineberg',
  uf: 'ES',
};

export const sampleWithPartialData: IAddress = {
  id: 21652,
  cep: 'timesXXX',
  street: 'Emmerich Knolls',
  number: 'which',
  city: 'Nilsshire',
  uf: 'RN',
};

export const sampleWithFullData: IAddress = {
  id: 16440,
  cep: 'ouch mola',
  street: 'Victoria Street',
  number: 'indeed und',
  complement: 'kick hmph blah',
  district: 'sans opposite',
  city: 'North Josefinatown',
  uf: 'SP',
};

export const sampleWithNewData: NewAddress = {
  cep: 'zowie uh-',
  street: 'Chestnut Close',
  city: 'Swaniawskishire',
  uf: 'AC',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
