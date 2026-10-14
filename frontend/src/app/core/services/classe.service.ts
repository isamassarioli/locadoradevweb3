import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { API_BASE_URL } from '../api.config';
import { Classe } from '../models/classe';

@Injectable({ providedIn: 'root' })
export class ClasseService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${API_BASE_URL}/classes`;

  listar(): Observable<Classe[]> {
    return this.http.get<Classe[]>(this.baseUrl);
  }

  consultar(id: number): Observable<Classe> {
    return this.http.get<Classe>(`${this.baseUrl}/${id}`);
  }

  incluir(classe: Classe): Observable<Classe> {
    return this.http.post<Classe>(this.baseUrl, classe);
  }

  alterar(id: number, classe: Classe): Observable<Classe> {
    return this.http.put<Classe>(`${this.baseUrl}/${id}`, classe);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
