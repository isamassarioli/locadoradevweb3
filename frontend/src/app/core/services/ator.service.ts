import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { API_BASE_URL } from '../api.config';
import { Ator } from '../models/ator';

@Injectable({ providedIn: 'root' })
export class AtorService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${API_BASE_URL}/atores`;

  listar(): Observable<Ator[]> {
    return this.http.get<Ator[]>(this.baseUrl);
  }

  consultar(id: number): Observable<Ator> {
    return this.http.get<Ator>(`${this.baseUrl}/${id}`);
  }

  incluir(ator: Ator): Observable<Ator> {
    return this.http.post<Ator>(this.baseUrl, ator);
  }

  alterar(id: number, ator: Ator): Observable<Ator> {
    return this.http.put<Ator>(`${this.baseUrl}/${id}`, ator);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
