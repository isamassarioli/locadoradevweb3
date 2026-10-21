import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { API_BASE_URL } from '../api.config';
import { Titulo, TituloRequest } from '../models/titulo';

@Injectable({ providedIn: 'root' })
export class TituloService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${API_BASE_URL}/titulos`;

  listar(): Observable<Titulo[]> {
    return this.http.get<Titulo[]>(this.baseUrl);
  }

  consultar(id: number): Observable<Titulo> {
    return this.http.get<Titulo>(`${this.baseUrl}/${id}`);
  }

  incluir(titulo: TituloRequest): Observable<Titulo> {
    return this.http.post<Titulo>(this.baseUrl, titulo);
  }

  alterar(id: number, titulo: TituloRequest): Observable<Titulo> {
    return this.http.put<Titulo>(`${this.baseUrl}/${id}`, titulo);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
