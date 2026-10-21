import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar } from '@angular/material/snack-bar';

import { Classe } from '../core/models/classe';
import { ClasseService } from '../core/services/classe.service';

@Component({
  selector: 'app-classe-list',
  imports: [RouterLink, MatTableModule, MatButtonModule, MatIconModule, MatCardModule],
  templateUrl: './classe-list.html',
  styleUrl: './classe-list.css'
})
export class ClasseList {
  private readonly service = inject(ClasseService);
  private readonly snackBar = inject(MatSnackBar);

  readonly colunas = ['id', 'nome', 'valor', 'prazoDevolucao', 'acoes'];
  readonly classes = signal<Classe[]>([]);

  constructor() {
    this.carregar();
  }

  carregar(): void {
    this.service.listar().subscribe({
      next: (lista) => this.classes.set(lista),
      error: () => this.snackBar.open('Erro ao carregar classes. O backend esta no ar?', 'Fechar', { duration: 5000 })
    });
  }

  excluir(classe: Classe): void {
    if (!classe.id) {
      return;
    }
    if (!confirm(`Confirma a exclusao da classe "${classe.nome}"?`)) {
      return;
    }
    this.service.excluir(classe.id).subscribe({
      next: () => {
        this.snackBar.open('Classe excluida.', 'Fechar', { duration: 3000 });
        this.carregar();
      },
      error: () => this.snackBar.open('Erro ao excluir classe.', 'Fechar', { duration: 4000 })
    });
  }
}
