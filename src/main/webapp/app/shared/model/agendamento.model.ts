import dayjs from 'dayjs';
import { IDrone } from 'app/shared/model/drone.model';
import { IPedido } from 'app/shared/model/pedido.model';
import { StatusAgendamento } from 'app/shared/model/enumerations/status-agendamento.model';

export interface IAgendamento {
  id?: string;
  dataAgendada?: string | null;
  statusAgendamento?: StatusAgendamento | null;
  criadoEm?: string | null;
  atualizadoEm?: string | null;
  drone?: IDrone | null;
  pedido?: IPedido | null;
}

export const defaultValue: Readonly<IAgendamento> = {};
