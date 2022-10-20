import dayjs from 'dayjs';
import { IEndereco } from 'app/shared/model/endereco.model';
import { ITelefone } from 'app/shared/model/telefone.model';
import { IPedido } from 'app/shared/model/pedido.model';
import { TipoEmpresa } from 'app/shared/model/enumerations/tipo-empresa.model';

export interface IEmpresa {
  id?: string;
  razaoSocial?: string;
  nomeFantasia?: string | null;
  documento?: number;
  email?: string | null;
  tipoEmpresa?: TipoEmpresa | null;
  status?: boolean | null;
  criadoEm?: string | null;
  atualizadoEm?: string | null;
  razaoSocials?: IEndereco[] | null;
  razaoSocials?: ITelefone[] | null;
  razaoSocials?: IPedido[] | null;
}

export const defaultValue: Readonly<IEmpresa> = {
  status: false,
};
