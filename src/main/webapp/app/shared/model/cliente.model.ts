import dayjs from 'dayjs';
import { IEndereco } from 'app/shared/model/endereco.model';
import { ITelefone } from 'app/shared/model/telefone.model';
import { IPedido } from 'app/shared/model/pedido.model';

export interface ICliente {
  id?: string;
  nome?: string;
  documento?: string;
  email?: string | null;
  status?: boolean | null;
  criadoEm?: string | null;
  atualizadoEm?: string | null;
  enderecos?: IEndereco[] | null;
  telefones?: ITelefone[] | null;
  pedidos?: IPedido[] | null;
}

export const defaultValue: Readonly<ICliente> = {
  status: false,
};
