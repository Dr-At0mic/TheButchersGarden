import { DateObject } from "../../systemutils/models/Models";

export const ConvertDateObjectToLocalDateTimeString = (
  dateObject: DateObject | undefined
): string => {
  const { $y, $M, $D, $H, $m, $s } = dateObject;
  return `${$y}-${String($M).padStart(2, "0")}-${String($D).padStart(
    2,
    "0"
  )} ${String($H).padStart(2, "0")}:${String($m).padStart(2, "0")}:${String(
    $s
  ).padStart(2, "0")}`;
};
