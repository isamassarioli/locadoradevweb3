import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', pathMatch: 'full', loadComponent: () => import('./inicio/inicio').then((m) => m.Inicio) },

  { path: 'atores', loadComponent: () => import('./atores/ator-list').then((m) => m.AtorList) },
  { path: 'atores/novo', loadComponent: () => import('./atores/ator-form').then((m) => m.AtorForm) },
  { path: 'atores/:id/editar', loadComponent: () => import('./atores/ator-form').then((m) => m.AtorForm) },

  { path: 'clientes', loadComponent: () => import('./clientes/cliente-list').then((m) => m.ClienteList) },
  { path: 'clientes/novo', loadComponent: () => import('./clientes/cliente-form').then((m) => m.ClienteForm) },
  { path: 'clientes/:id/editar', loadComponent: () => import('./clientes/cliente-form').then((m) => m.ClienteForm) },

  { path: '**', redirectTo: '' }
];
