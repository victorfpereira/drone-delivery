import dayjs from 'dayjs';
import { IPedido } from 'app/shared/model/pedido.model';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { ICliente } from 'app/shared/model/cliente.model';
import { TipoEndereco } from 'app/shared/model/enumerations/tipo-endereco.model';

export interface IEndereco {
  id?: string;
  rua?: string | null;
  numero?: number | null;
  bairro?: string | null;
  cidade?: string | null;
  estado?: string | null;
  cep?: string | null;
  complemento?: string | null;
  referencia?: string | null;
  enderecoCompleto?: string | null;
  tipoEndereco?: TipoEndereco | null;
  latitude?: number | null;
  longitude?: number | null;
  status?: boolean | null;
  criadoEm?: string | null;
  atualizadoEm?: string | null;
  enderecoCompletos?: IPedido[] | null;
  empresa?: IEmpresa | null;
  cliente?: ICliente | null;
}

export const defaultValue: Readonly<IEndereco> = {
  status: false,
};
