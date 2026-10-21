import { Titulo } from './titulo';
export interface Item { id?: number; numSerie: string; dataAquisicao: string; tipoItem: 'FITA' | 'DVD' | 'BLURAY'; titulo: Titulo; }
export interface ItemRequest { numSerie: string; dataAquisicao: string; tipoItem: 'FITA' | 'DVD' | 'BLURAY'; tituloId: number; }
