import { TimeValidationError } from "@mui/x-date-pickers";

export interface Response {
  status: boolean;
  message: string;
  data: unknown;
  ErrorCode: string;
  httpStatus: string;
}

export interface AvailableTableData {
  resId: number;
  tableId: number;
  isAc: boolean;
  tableCount: number;
  tablePrice: string;
  bookingStarts: Date;
  bookingEnds: Date;
  tableType: tableType;
}

export interface tableType {
  tableTypeId: number;
  tableTypeName: string;
  seats: number;
}

export interface ReserveTableRequest {
  tableId: number;
  count: number;
  price: string;
  bookingStarts: string;
  bookingEnd: string;
}
export interface DateObject {
  $L: string;
  $u: any;
  $d: string;
  $y: number;
  $M: number;
  $D: number;
  $W: number;
  $H: number;
  $m: number;
  $s: number;
  $ms: number;
  $x: any;
  $isDayjsObject: boolean;
}

export interface EditReservationRequest {
  reservationId: number;
  count: number;
  tableId: number;
  editStart: string;
  editEnd: string;
}
