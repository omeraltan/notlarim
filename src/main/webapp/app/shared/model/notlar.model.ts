import { INotBaslikTanim } from 'app/shared/model/not-baslik-tanim.model';

export interface INotlar {
  id?: number;
  not?: string;
  baslik?: INotBaslikTanim | null;
}

export const defaultValue: Readonly<INotlar> = {};
