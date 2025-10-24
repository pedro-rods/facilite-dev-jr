export interface IDepartment {
  id: number;
  name?: string | null;
  costCenter?: string | null;
}

export type NewDepartment = Omit<IDepartment, 'id'> & { id: null };
