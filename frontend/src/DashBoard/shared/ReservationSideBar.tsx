import { HeartIcon } from "@heroicons/react/24/outline";
import couples from "../../../src/assets/couples.jpg";
import {
  AvailableTableData,
  DateObject,
  ReserveTableRequest,
  Response,
} from "../../systemutils/models/Models";
import React, { useEffect, useState } from "react";
import CloseIcon from "@mui/icons-material/Close";
import { DemoContainer } from "@mui/x-date-pickers/internals/demo";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import axios from "axios";
import { ConvertDateObjectToLocalDateTimeString } from "./ConvertDateObjectToLocalDateTimeString.tsx";
import { UseAuth } from "./UseAuth.tsx";
import SuccessPopup from "../../components/common/PopUs/SuccessPopup.tsx";
interface AvailableTablesProps {
  availableTables: AvailableTableData;
}
export const ReservationSideBar: React.FC<AvailableTablesProps> = ({
  availableTables,
}) => {
  const [toggle, setToggle] = useState<boolean>(true);
  const [screenWidth, setScreenWidth] = useState<number>(window.innerWidth);
  const [reservationStart, setReservationStart] = useState<DateObject>();
  const [reservationEnd, setReservationEnd] = useState<DateObject>();
  const [popup, setPopup] = useState<any>(<></>);
  const [processStatus, setProcessStatus] = useState<boolean>(false);
  const [popupMessage, setPopupMessage] = useState<string>("");
  const [errorCodes, setErrorCodes] = useState<string>("");
  const sidebarToggle = () => {
    setToggle(!toggle);
  };
  useEffect(() => {
    const handleScreenWidthChange = () => {
      setScreenWidth(window.innerWidth);
    };
    handleScreenWidthChange();
    console.log(screenWidth);
    window.addEventListener("resize", handleScreenWidthChange);
    return () => {
      window.removeEventListener("resize", handleScreenWidthChange);
    };
  }, []);
  useEffect(() => {
    setToggle(true);
    console.log(toggle);
  }, [availableTables]);

  const reserveTable = async () => {
    const body: ReserveTableRequest = {
      tableId: availableTables.tableId,
      count: 1,
      price: availableTables.tablePrice,
      bookingEnd: ConvertDateObjectToLocalDateTimeString(reservationStart),
      bookingStarts: ConvertDateObjectToLocalDateTimeString(reservationEnd),
    };

    try {
      console.log(UseAuth());
      const serverResponse = await axios.post(
        import.meta.env.VITE_GATEWAY_DOMAIN + "reservation/reserveTable",
        body,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${UseAuth()}`,
          },
          withCredentials: true,
        }
      );
      const response: Response = serverResponse.data;
      console.log(response);
      if (!response.status) setErrorCodes(response.ErrorCode);
      else {
        setProcessStatus(true);
        setPopupMessage(response.message);
      }
    } catch (err) {
      console.log(err);
    }
  };
  useEffect(() => {
    if (processStatus)
      setPopup(
        <SuccessPopup
          title="Table Reserved Successfully"
          message={popupMessage}
          path="/dashBoard"
          butonName="Got to Reservation Chart"
        />
      );
  }, [processStatus]);

  return (
    <>
      {popup}
      <aside
        className={`w-[21rem]  overflow-y-auto border-l mb-3 border-gray-200 bg-white p-8 z-50 ${
          screenWidth > 800
            ? "block"
            : `fixed lg:relative top-0 right-0 ${toggle ? "block" : "hidden"}`
        } `}
      >
        <div
          onClick={() => {
            sidebarToggle();
          }}
          className={`top-1 left-1 cursor-pointer border rounded-full ${
            screenWidth > 800 ? "hidden" : "absolute"
          } `}
        >
          <CloseIcon />
        </div>
        <div className="space-y-3 overflow-y-auto">
          <div>
            <div
              className={`aspect-w-10 aspect-h-7 block w-full overflow-hidden rounded-lg `}
            >
              <img src={couples} alt="" className="object-cover" />
            </div>
            <div className="mt-8 flex items-start justify-between">
              <div>
                <h2 className="text-lg font-medium text-gray-900">
                  <span className="sr-only">Details for </span>
                  {availableTables.tableType.tableTypeName}
                </h2>
              </div>
              <button
                type="button"
                className="ml-4 flex h-8 w-8 items-center justify-center rounded-full bg-white text-gray-400 hover:bg-gray-100 hover:text-gray-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
              >
                <HeartIcon className="h-6 w-6" aria-hidden="true" />
                <span className="sr-only">Favorite</span>
              </button>
            </div>
          </div>
          <div>
            <h3 className="font-medium text-gray-900">Information</h3>
            <dl className="mt-2 divide-y divide-gray-200 border-t border-b border-gray-200">
              {(availableTables.tableCount == 0 || errorCodes === "302") && (
                <div className="flex justify-center py-3 text-sm font-medium">
                  <dt className="text-red-600">Table Unavailable</dt>
                </div>
              )}
              <div className="flex justify-between py-3 text-sm font-medium">
                <dt className="text-gray-500">Seats</dt>
                <dd className="whitespace-nowrap text-gray-900">
                  {availableTables.tableType.seats}
                </dd>
              </div>
              <div className="flex justify-between py-3 text-sm font-medium">
                <dt className="text-gray-500">Price</dt>
                <dd className="whitespace-nowrap text-gray-900">
                  {availableTables.tablePrice}
                </dd>
              </div>

              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DemoContainer
                  sx={{ paddingTop: 4 }}
                  components={["DateTimePicker"]}
                >
                  <DateTimePicker
                    sx={{ width: 180 }}
                    onChange={(newValue) => {
                      setReservationStart(newValue); // Log the selected date/time value
                    }}
                    label="Reservation Starting Time"
                  />
                </DemoContainer>
              </LocalizationProvider>

              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DemoContainer
                  sx={{ paddingTop: 4 }}
                  components={["DateTimePicker"]}
                >
                  <DateTimePicker
                    sx={{ width: 180 }}
                    onChange={(newValue) => {
                      setReservationEnd(newValue); // Log the selected date/time value
                    }}
                    label="Reservation Ending Time"
                  />
                </DemoContainer>
              </LocalizationProvider>
            </dl>
          </div>

          <div className="flex">
            <button
              disabled={availableTables.tableCount === 0}
              onClick={() => reserveTable()}
              type="button"
              className="flex-1 mt-1 rounded-md border border-transparent bg-indigo-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
            >
              Reserve
            </button>
          </div>
        </div>
      </aside>
    </>
  );
};
