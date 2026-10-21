import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ItemService } from '../core/services/item.service';
import { TituloService } from '../core/services/titulo.service';
import { Titulo } from '../core/models/titulo';
@Component({ selector: 'app-item-form', imports: [ReactiveFormsModule, RouterLink, MatSelectModule, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule], templateUrl: './item-form.html', styleUrl: './item-form.css' })
export class ItemForm {
 private readonly service=inject(ItemService);
 private readonly route=inject(ActivatedRoute);
 private readonly router=inject(Router);
 private readonly fb=inject(FormBuilder);
 private readonly snackBar=inject(MatSnackBar);
 readonly id=Number(this.route.snapshot.paramMap.get('id')) || null;
 readonly salvando=signal(false);
 readonly carregando=signal(true);
 readonly falha=signal(false);
 readonly titulos=signal<Titulo[]>([]);
 private readonly titulosService=inject(TituloService);
 readonly form=this.fb.nonNullable.group({numSerie: ['', Validators.required], dataAquisicao: [new Date().toLocaleDateString('en-CA'), Validators.required], tipoItem: ['DVD' as 'FITA' | 'DVD' | 'BLURAY', Validators.required], tituloId: [0, Validators.min(1)]});
 constructor() {
 this.titulosService.listar().subscribe({ next: dados=>this.titulos.set(dados), error: ()=>{this.falha.set(true); this.snackBar.open('Erro ao carregar titulos.','Fechar');} });
 if(this.id) this.service.consultar(this.id).subscribe({next: dados=>{this.form.patchValue({ numSerie: dados.numSerie, dataAquisicao: dados.dataAquisicao, tipoItem: dados.tipoItem, tituloId: dados.titulo.id! });this.carregando.set(false);},error: ()=>{this.snackBar.open('Registro nao encontrado.','Fechar');this.router.navigate(['/itens']);}}); else this.carregando.set(false);
 }
 salvar(): void {
 if(this.form.invalid || this.salvando() || this.carregando() || this.falha()) {this.form.markAllAsTouched();return;}
 this.salvando.set(true);
 const dados=this.form.getRawValue();
 const operacao=this.id ? this.service.alterar(this.id,dados) : this.service.incluir(dados);
 operacao.subscribe({ next: ()=>{this.snackBar.open('Registro salvo.','Fechar',{duration:3000});this.router.navigate(['/itens']);},error: erro=>{this.salvando.set(false);this.snackBar.open(erro.error?.message || 'Erro ao salvar. Verifique os campos.','Fechar',{duration:6000});} });
 }
}