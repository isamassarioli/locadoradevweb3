import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { API_BASE_URL } from '../api.config';
import { Diretor } from '../models/diretor';

@Injectable({ providedIn: 'root' })
export class DiretorService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${API_BASE_URL}/diretores`;

  listar(): Observable<Diretor[]> {
    return this.http.get<Diretor[]>(this.baseUrl);
  }

  consultar(id: number): Observable<Diretor> {
    return this.http.get<Diretor>(`${this.baseUrl}/${id}`);
  }

  incluir(diretor: Diretor): Observable<Diretor> {
    return this.http.post<Diretor>(this.baseUrl, diretor);
  }

  alterar(id: number, diretor: Diretor): Observable<Diretor> {
    return this.http.put<Diretor>(`${this.baseUrl}/${id}`, diretor);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
