import { Ator } from './ator';
import { Diretor } from './diretor';
import { Classe } from './classe';
export interface Titulo { id?: number; nome: string; ano: number; sinopse: string; categoria: string; diretor: Diretor; classe: Classe; atores: Ator[]; }
export interface TituloRequest { nome: string; ano: number; sinopse: string; categoria: string; diretorId: number; classeId: number; atoresIds: number[]; }
