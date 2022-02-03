import { IPersonelTanim } from 'app/shared/model/personel-tanim.model';

export interface IRehber {
  id?: number;
  telefon?: number;
  adres?: string | null;
  personel?: IPersonelTanim | null;
}

export const defaultValue: Readonly<IRehber> = {};
