import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';

import { AtorService } from '../core/services/ator.service';

@Component({
  selector: 'app-ator-form',
  imports: [ReactiveFormsModule, RouterLink, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './ator-form.html',
  styleUrl: './ator-form.css'
})
export class AtorForm {
  private readonly service = inject(AtorService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);
  private readonly snackBar = inject(MatSnackBar);

  readonly id = signal<number | null>(null);
  readonly edicao = signal(false);

  readonly form = this.fb.nonNullable.group({
    nome: ['', Validators.required]
  });

  constructor() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.id.set(id);
      this.edicao.set(true);
      this.service.consultar(id).subscribe({
        next: (ator) => this.form.patchValue({ nome: ator.nome }),
        error: () => {
          this.snackBar.open('Ator nao encontrado.', 'Fechar', { duration: 4000 });
          this.router.navigate(['/atores']);
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
        this.snackBar.open('Ator salvo com sucesso.', 'Fechar', { duration: 3000 });
        this.router.navigate(['/atores']);
      },
      error: () => this.snackBar.open('Erro ao salvar ator.', 'Fechar', { duration: 4000 })
    });
  }
}
