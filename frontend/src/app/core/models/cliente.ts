export interface Cliente {
  id?: number;
  nome: string;
  cpf: string;
  endereco?: string;
  sexo?: string;
  tipo?: string;
  comunicados: boolean;
  obs?: string;
}
