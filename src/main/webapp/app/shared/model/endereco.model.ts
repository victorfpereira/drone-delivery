import dayjs from 'dayjs';
import { IPedido } from 'app/shared/model/pedido.model';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { ICliente } from 'app/shared/model/cliente.model';
import { TipoEndereco } from 'app/shared/model/enumerations/tipo-endereco.model';

export interface IEndereco {
  id?: string;
  rua?: string;
  numero?: string;
  bairro?: string;
  cidade?: string;
  estado?: string;
  cep?: string;
  complemento?: string | null;
  referencia?: string | null;
  tipoEndereco?: TipoEndereco | null;
  latitude?: number | null;
  longitude?: number | null;
  status?: boolean | null;
  criadoEm?: string | null;
  atualizadoEm?: string | null;
  pedidos?: IPedido[] | null;
  empresa?: IEmpresa | null;
  cliente?: ICliente | null;
}

export const defaultValue: Readonly<IEndereco> = {
  status: false,
};
