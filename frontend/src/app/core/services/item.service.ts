import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { API_BASE_URL } from '../api.config';
import { Item, ItemRequest } from '../models/item';

@Injectable({ providedIn: 'root' })
export class ItemService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${API_BASE_URL}/itens`;

  listar(): Observable<Item[]> {
    return this.http.get<Item[]>(this.baseUrl);
  }

  consultar(id: number): Observable<Item> {
    return this.http.get<Item>(`${this.baseUrl}/${id}`);
  }

  incluir(item: ItemRequest): Observable<Item> {
    return this.http.post<Item>(this.baseUrl, item);
  }

  alterar(id: number, item: ItemRequest): Observable<Item> {
    return this.http.put<Item>(`${this.baseUrl}/${id}`, item);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
