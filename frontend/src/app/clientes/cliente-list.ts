import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar } from '@angular/material/snack-bar';

import { Cliente } from '../core/models/cliente';
import { ClienteService } from '../core/services/cliente.service';

@Component({
  selector: 'app-cliente-list',
  imports: [RouterLink, MatTableModule, MatButtonModule, MatIconModule, MatCardModule],
  templateUrl: './cliente-list.html',
  styleUrl: './cliente-list.css'
})
export class ClienteList {
  private readonly service = inject(ClienteService);
  private readonly snackBar = inject(MatSnackBar);

  readonly colunas = ['id', 'nome', 'cpf', 'tipo', 'comunicados', 'acoes'];
  readonly clientes = signal<Cliente[]>([]);

  constructor() {
    this.carregar();
  }

  carregar(): void {
    this.service.listar().subscribe({
      next: (lista) => this.clientes.set(lista),
      error: () => this.snackBar.open('Erro ao carregar clientes. O backend esta no ar?', 'Fechar', { duration: 5000 })
    });
  }

  excluir(cliente: Cliente): void {
    if (!cliente.id) {
      return;
    }
    if (!confirm(`Confirma a exclusao do cliente "${cliente.nome}"?`)) {
      return;
    }
    this.service.excluir(cliente.id).subscribe({
      next: () => {
        this.snackBar.open('Cliente excluido.', 'Fechar', { duration: 3000 });
        this.carregar();
      },
      error: () => this.snackBar.open('Erro ao excluir cliente.', 'Fechar', { duration: 4000 })
    });
  }
}
