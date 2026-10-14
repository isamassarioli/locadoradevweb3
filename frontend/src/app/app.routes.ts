import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', pathMatch: 'full', loadComponent: () => import('./inicio/inicio').then((m) => m.Inicio) },

  { path: 'atores', loadComponent: () => import('./atores/ator-list').then((m) => m.AtorList) },
  { path: 'atores/novo', loadComponent: () => import('./atores/ator-form').then((m) => m.AtorForm) },
  { path: 'atores/:id/editar', loadComponent: () => import('./atores/ator-form').then((m) => m.AtorForm) },

  { path: 'classes', loadComponent: () => import('./classes/classe-list').then((m) => m.ClasseList) },
  { path: 'classes/novo', loadComponent: () => import('./classes/classe-form').then((m) => m.ClasseForm) },
  { path: 'classes/:id/editar', loadComponent: () => import('./classes/classe-form').then((m) => m.ClasseForm) },

  { path: 'diretores', loadComponent: () => import('./diretores/diretor-list').then((m) => m.DiretorList) },
  { path: 'diretores/novo', loadComponent: () => import('./diretores/diretor-form').then((m) => m.DiretorForm) },
  { path: 'diretores/:id/editar', loadComponent: () => import('./diretores/diretor-form').then((m) => m.DiretorForm) },

  { path: '**', redirectTo: '' }
];
