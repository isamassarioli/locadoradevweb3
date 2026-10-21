import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TituloService } from '../core/services/titulo.service';
import { AtorService } from '../core/services/ator.service';
import { Ator } from '../core/models/ator';
import { DiretorService } from '../core/services/diretor.service';
import { Diretor } from '../core/models/diretor';
import { ClasseService } from '../core/services/classe.service';
import { Classe } from '../core/models/classe';
@Component({ selector: 'app-titulo-form', imports: [ReactiveFormsModule, RouterLink, MatSelectModule, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule], templateUrl: './titulo-form.html', styleUrl: './titulo-form.css' })
export class TituloForm {
 private readonly service=inject(TituloService);
 private readonly route=inject(ActivatedRoute);
 private readonly router=inject(Router);
 private readonly fb=inject(FormBuilder);
 private readonly snackBar=inject(MatSnackBar);
 readonly id=Number(this.route.snapshot.paramMap.get('id')) || null;
 readonly salvando=signal(false);
 readonly carregando=signal(true);
 readonly falha=signal(false);
 readonly atores=signal<Ator[]>([]);
 private readonly atoresService=inject(AtorService);
 readonly diretores=signal<Diretor[]>([]);
 private readonly diretoresService=inject(DiretorService);
 readonly classes=signal<Classe[]>([]);
 private readonly classesService=inject(ClasseService);
 readonly form=this.fb.nonNullable.group({nome: ['', Validators.required], ano: [new Date().getFullYear(), [Validators.required, Validators.min(1888), Validators.max(2100), Validators.pattern(/^[0-9]+$/)]], sinopse: ['', [Validators.required, Validators.maxLength(5000)]], categoria: ['', Validators.required], diretorId: [0, Validators.min(1)], classeId: [0, Validators.min(1)], atoresIds: [[] as number[], Validators.required]});
 constructor() {
 this.atoresService.listar().subscribe({ next: dados=>this.atores.set(dados), error: ()=>{this.falha.set(true); this.snackBar.open('Erro ao carregar atores.','Fechar');} });
 this.diretoresService.listar().subscribe({ next: dados=>this.diretores.set(dados), error: ()=>{this.falha.set(true); this.snackBar.open('Erro ao carregar diretores.','Fechar');} });
 this.classesService.listar().subscribe({ next: dados=>this.classes.set(dados), error: ()=>{this.falha.set(true); this.snackBar.open('Erro ao carregar classes.','Fechar');} });
 if(this.id) this.service.consultar(this.id).subscribe({next: dados=>{this.form.patchValue({ nome: dados.nome, ano: dados.ano, sinopse: dados.sinopse, categoria: dados.categoria, diretorId: dados.diretor.id!, classeId: dados.classe.id!, atoresIds: dados.atores.map(a=>a.id!) });this.carregando.set(false);},error: ()=>{this.snackBar.open('Registro nao encontrado.','Fechar');this.router.navigate(['/titulos']);}}); else this.carregando.set(false);
 }
 salvar(): void {
 if(this.form.invalid || this.salvando() || this.carregando() || this.falha()) {this.form.markAllAsTouched();return;}
 this.salvando.set(true);
 const dados=this.form.getRawValue();
 const operacao=this.id ? this.service.alterar(this.id,dados) : this.service.incluir(dados);
 operacao.subscribe({ next: ()=>{this.snackBar.open('Registro salvo.','Fechar',{duration:3000});this.router.navigate(['/titulos']);},error: erro=>{this.salvando.set(false);this.snackBar.open(erro.error?.message || 'Erro ao salvar. Verifique os campos.','Fechar',{duration:6000});} });
 }
}