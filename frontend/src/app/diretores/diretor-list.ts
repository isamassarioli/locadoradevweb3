import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar } from '@angular/material/snack-bar';

import { Diretor } from '../core/models/diretor';
import { DiretorService } from '../core/services/diretor.service';

@Component({
  selector: 'app-diretor-list',
  imports: [RouterLink, MatTableModule, MatButtonModule, MatIconModule, MatCardModule],
  templateUrl: './diretor-list.html',
  styleUrl: './diretor-list.css'
})
export class DiretorList {
  private readonly service = inject(DiretorService);
  private readonly snackBar = inject(MatSnackBar);

  readonly colunas = ['id', 'nome', 'acoes'];
  readonly diretores = signal<Diretor[]>([]);

  constructor() {
    this.carregar();
  }

  carregar(): void {
    this.service.listar().subscribe({
      next: (lista) => this.diretores.set(lista),
      error: () => this.snackBar.open('Erro ao carregar diretores. O backend esta no ar?', 'Fechar', { duration: 5000 })
    });
  }

  excluir(diretor: Diretor): void {
    if (!diretor.id) {
      return;
    }
    if (!confirm(`Confirma a exclusao do diretor "${diretor.nome}"?`)) {
      return;
    }
    this.service.excluir(diretor.id).subscribe({
      next: () => {
        this.snackBar.open('Diretor excluido.', 'Fechar', { duration: 3000 });
        this.carregar();
      },
      error: () => this.snackBar.open('Erro ao excluir diretor.', 'Fechar', { duration: 4000 })
    });
  }
}
