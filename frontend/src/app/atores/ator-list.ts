import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar } from '@angular/material/snack-bar';

import { Ator } from '../core/models/ator';
import { AtorService } from '../core/services/ator.service';

@Component({
  selector: 'app-ator-list',
  imports: [RouterLink, MatTableModule, MatButtonModule, MatIconModule, MatCardModule],
  templateUrl: './ator-list.html',
  styleUrl: './ator-list.css'
})
export class AtorList {
  private readonly service = inject(AtorService);
  private readonly snackBar = inject(MatSnackBar);

  readonly colunas = ['id', 'nome', 'acoes'];
  readonly atores = signal<Ator[]>([]);

  constructor() {
    this.carregar();
  }

  carregar(): void {
    this.service.listar().subscribe({
      next: (lista) => this.atores.set(lista),
      error: () => this.snackBar.open('Erro ao carregar atores. O backend esta no ar?', 'Fechar', { duration: 5000 })
    });
  }

  excluir(ator: Ator): void {
    if (!ator.id) {
      return;
    }
    if (!confirm(`Confirma a exclusao do ator "${ator.nome}"?`)) {
      return;
    }
    this.service.excluir(ator.id).subscribe({
      next: () => {
        this.snackBar.open('Ator excluido.', 'Fechar', { duration: 3000 });
        this.carregar();
      },
      error: () => this.snackBar.open('Erro ao excluir ator.', 'Fechar', { duration: 4000 })
    });
  }
}
