import dayjs from 'dayjs';
import { IAgendamento } from 'app/shared/model/agendamento.model';
import { ICliente } from 'app/shared/model/cliente.model';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { IEndereco } from 'app/shared/model/endereco.model';
import { StatusPedido } from 'app/shared/model/enumerations/status-pedido.model';

export interface IPedido {
  id?: string;
  codigo?: number;
  notaFiscal?: number;
  statusPedido?: StatusPedido | null;
  criadoEm?: string | null;
  atualizadoEm?: string | null;
  codigos?: IAgendamento[] | null;
  cliente?: ICliente | null;
  empresa?: IEmpresa | null;
  endereco?: IEndereco | null;
}

export const defaultValue: Readonly<IPedido> = {};
