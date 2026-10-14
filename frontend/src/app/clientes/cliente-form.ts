import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ClienteService } from '../core/services/cliente.service';

@Component({
  selector: 'app-cliente-form',
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
    MatButtonModule
  ],
  templateUrl: './cliente-form.html',
  styleUrl: './cliente-form.css'
})
export class ClienteForm {
  private readonly service = inject(ClienteService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);
  private readonly snackBar = inject(MatSnackBar);

  readonly id = signal<number | null>(null);
  readonly edicao = signal(false);

  readonly form = this.fb.nonNullable.group({
    nome: ['', Validators.required],
    cpf: ['', Validators.required],
    endereco: [''],
    sexo: [''],
    tipo: [''],
    comunicados: [false],
    obs: ['']
  });

  constructor() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.id.set(id);
      this.edicao.set(true);
      this.service.consultar(id).subscribe({
        next: (cliente) => this.form.patchValue(cliente),
        error: () => {
          this.snackBar.open('Cliente nao encontrado.', 'Fechar', { duration: 4000 });
          this.router.navigate(['/clientes']);
        }
      });
    }
  }

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const dados = this.form.getRawValue();
    const operacao = this.edicao()
      ? this.service.alterar(this.id()!, dados)
      : this.service.incluir(dados);

    operacao.subscribe({
      next: () => {
        this.snackBar.open('Cliente salvo com sucesso.', 'Fechar', { duration: 3000 });
        this.router.navigate(['/clientes']);
      },
      error: () => this.snackBar.open('Erro ao salvar cliente.', 'Fechar', { duration: 4000 })
    });
  }
}
