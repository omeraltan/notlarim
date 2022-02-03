import { INotlar } from 'app/shared/model/notlar.model';

export interface INotBaslikTanim {
  id?: number;
  baslik?: string;
  notlars?: INotlar[] | null;
}

export const defaultValue: Readonly<INotBaslikTanim> = {};
