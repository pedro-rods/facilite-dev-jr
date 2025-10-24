import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'devjrApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'department',
    data: { pageTitle: 'devjrApp.department.home.title' },
    loadChildren: () => import('./department/department.routes'),
  },
  {
    path: 'address',
    data: { pageTitle: 'devjrApp.address.home.title' },
    loadChildren: () => import('./address/address.routes'),
  },
  {
    path: 'employee',
    data: { pageTitle: 'devjrApp.employee.home.title' },
    loadChildren: () => import('./employee/employee.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
