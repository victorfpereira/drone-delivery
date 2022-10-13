import dayjs from 'dayjs';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { ICliente } from 'app/shared/model/cliente.model';
import { TipoTelefone } from 'app/shared/model/enumerations/tipo-telefone.model';

export interface ITelefone {
  id?: string;
  ddd?: string;
  numero?: string;
  tipoTelefone?: TipoTelefone | null;
  status?: boolean | null;
  criadoEm?: string | null;
  atualizadoEm?: string | null;
  empresa?: IEmpresa | null;
  cliente?: ICliente | null;
}

export const defaultValue: Readonly<ITelefone> = {
  status: false,
};
