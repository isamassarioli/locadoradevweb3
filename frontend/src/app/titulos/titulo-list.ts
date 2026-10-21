import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar } from '@angular/material/snack-bar';

import { Titulo } from '../core/models/titulo';
import { TituloService } from '../core/services/titulo.service';

@Component({
  selector: 'app-titulo-list',
  imports: [RouterLink, MatTableModule, MatButtonModule, MatIconModule, MatCardModule],
  templateUrl: './titulo-list.html',
  styleUrl: './titulo-list.css'
})
export class TituloList {
  private readonly service = inject(TituloService);
  private readonly snackBar = inject(MatSnackBar);

  readonly colunas = ['id', 'nome', 'ano', 'categoria', 'diretor', 'classe', 'acoes'];
  readonly titulos = signal<Titulo[]>([]);

  constructor() {
    this.carregar();
  }

  carregar(): void {
    this.service.listar().subscribe({
      next: (lista) => this.titulos.set(lista),
      error: (erro) => this.snackBar.open('Erro ao carregar titulos. O backend esta no ar?', 'Fechar', { duration: 5000 })
    });
  }

  excluir(titulo: Titulo): void {
    if (!titulo.id) {
      return;
    }
    if (!confirm(`Confirma a exclusao do titulo "${titulo.nome}"?`)) {
      return;
    }
    this.service.excluir(titulo.id).subscribe({
      next: () => {
        this.snackBar.open('Titulo excluido.', 'Fechar', { duration: 3000 });
        this.carregar();
      },
      error: (erro) => this.snackBar.open(erro.error?.message || 'Erro ao excluir titulo.', 'Fechar', { duration: 4000 })
    });
  }
}
