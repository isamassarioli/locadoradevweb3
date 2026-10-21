import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ClasseService } from '../core/services/classe.service';

@Component({
  selector: 'app-classe-form',
  imports: [ReactiveFormsModule, RouterLink, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './classe-form.html',
  styleUrl: './classe-form.css'
})
export class ClasseForm {
  private readonly service = inject(ClasseService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);
  private readonly snackBar = inject(MatSnackBar);

  readonly id = signal<number | null>(null);
  readonly edicao = signal(false);

  readonly form = this.fb.nonNullable.group({
    nome: ['', Validators.required],
    valor: [0, [Validators.required, Validators.min(0.01)]],
    prazoDevolucao: [1, [Validators.required, Validators.min(1), Validators.pattern(/^[0-9]+$/)]]
  });

  constructor() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.id.set(id);
      this.edicao.set(true);
      this.service.consultar(id).subscribe({
        next: (classe) => this.form.patchValue(classe),
        error: () => {
          this.snackBar.open('Classe nao encontrada.', 'Fechar', { duration: 4000 });
          this.router.navigate(['/classes']);
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
        this.snackBar.open('Classe salva com sucesso.', 'Fechar', { duration: 3000 });
        this.router.navigate(['/classes']);
      },
      error: () => this.snackBar.open('Erro ao salvar classe.', 'Fechar', { duration: 4000 })
    });
  }
}
