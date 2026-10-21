import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar } from '@angular/material/snack-bar';

import { Item } from '../core/models/item';
import { ItemService } from '../core/services/item.service';

@Component({
  selector: 'app-item-list',
  imports: [RouterLink, MatTableModule, MatButtonModule, MatIconModule, MatCardModule],
  templateUrl: './item-list.html',
  styleUrl: './item-list.css'
})
export class ItemList {
  private readonly service = inject(ItemService);
  private readonly snackBar = inject(MatSnackBar);

  readonly colunas = ['id', 'numSerie', 'titulo', 'tipoItem', 'dataAquisicao', 'acoes'];
  readonly itens = signal<Item[]>([]);

  constructor() {
    this.carregar();
  }

  carregar(): void {
    this.service.listar().subscribe({
      next: (lista) => this.itens.set(lista),
      error: (erro) => this.snackBar.open('Erro ao carregar itens. O backend esta no ar?', 'Fechar', { duration: 5000 })
    });
  }

  excluir(item: Item): void {
    if (!item.id) {
      return;
    }
    if (!confirm(`Confirma a exclusao do item "${item.numSerie}"?`)) {
      return;
    }
    this.service.excluir(item.id).subscribe({
      next: () => {
        this.snackBar.open('Item excluido.', 'Fechar', { duration: 3000 });
        this.carregar();
      },
      error: (erro) => this.snackBar.open(erro.error?.message || 'Erro ao excluir item.', 'Fechar', { duration: 4000 })
    });
  }
}
