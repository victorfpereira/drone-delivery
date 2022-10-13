import dayjs from 'dayjs';
import { IEndereco } from 'app/shared/model/endereco.model';
import { ITelefone } from 'app/shared/model/telefone.model';
import { IPedido } from 'app/shared/model/pedido.model';
import { TipoEmpresa } from 'app/shared/model/enumerations/tipo-empresa.model';

export interface IEmpresa {
  id?: string;
  razaoSocial?: string;
  nomeFantasia?: string | null;
  documento?: string;
  email?: string | null;
  tipoEmpresa?: TipoEmpresa | null;
  status?: boolean | null;
  criadoEm?: string | null;
  atualizadoEm?: string | null;
  enderecos?: IEndereco[] | null;
  telefones?: ITelefone[] | null;
  pedidos?: IPedido[] | null;
}

export const defaultValue: Readonly<IEmpresa> = {
  status: false,
};
