import React, { Fragment, useRef, useState } from "react";
import { Dialog, Transition } from "@headlessui/react";
import EditIcon from "@mui/icons-material/Edit";
import axios from "axios";
import { UseAuth } from "../../shared/UseAuth.tsx";
import {
  Response,
  DateObject,
  EditReservationRequest,
} from "../../../systemutils/models/Models";
import { ConvertDateObjectToLocalDateTimeString } from "../../shared/ConvertDateObjectToLocalDateTimeString.tsx";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { DemoContainer } from "@mui/x-date-pickers/internals/demo";
import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import { useNavigate } from "react-router-dom";

interface ChildComponentProps {
  active: boolean;
  setActive: React.Dispatch<React.SetStateAction<boolean>>;
  resId: number;
  tableCount: number;
  tableId: number;
}
export const EditReservation = ({
  active,
  setActive,
  resId,
  tableCount,
  tableId,
}: ChildComponentProps) => {
  const [count, setCount] = useState(tableCount);
  const [start, setStart] = useState<DateObject>();
  const [end, setEnd] = useState<DateObject>();

  const [responseC, setResponseC] = useState<Response>();
  const cancelButtonRef = useRef(null);
  const navigate = useNavigate();

  const editReservation = async () => {
    const body: EditReservationRequest = {
      reservationId: resId,
      count: count,
      tableId: tableId,
      editStart: ConvertDateObjectToLocalDateTimeString(start),
      editEnd: ConvertDateObjectToLocalDateTimeString(end),
    };
    await axios
      .put(
        `${import.meta.env.VITE_GATEWAY_DOMAIN}reservation/editReservation`,
        body,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${UseAuth()}`,
          },
          withCredentials: true,
        }
      )
      .then((response) => {
        const ServerResponse = response.data;
        setResponseC(ServerResponse);
        if (responseC?.status) {
          setResponseC({
            message: "Success",
            status: true,
            data: "",
            ErrorCode: "",
            httpStatus: "",
          });
          navigate("/dashBoard/bookingChart");
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
  return (
    <>
      <Transition.Root show={active} as={Fragment}>
        <Dialog
          as="div"
          className="relative z-10"
          initialFocus={cancelButtonRef}
          onClose={setActive}
        >
          <Transition.Child
            as={Fragment}
            enter="ease-out duration-300"
            enterFrom="opacity-0"
            enterTo="opacity-100"
            leave="ease-in duration-200"
            leaveFrom="opacity-100"
            leaveTo="opacity-0"
          >
            <div className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" />
          </Transition.Child>

          <div className="fixed inset-0 z-10 overflow-y-auto">
            <div className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
              <Transition.Child
                as={Fragment}
                enter="ease-out duration-300"
                enterFrom="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                enterTo="opacity-100 translate-y-0 sm:scale-100"
                leave="ease-in duration-200"
                leaveFrom="opacity-100 translate-y-0 sm:scale-100"
                leaveTo="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
              >
                <Dialog.Panel className="relative transform overflow-hidden rounded-lg bg-white px-4 pt-5 pb-4 text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg sm:p-6">
                  <div>
                    <div className="mx-auto flex h-12 w-12 items-center justify-center rounded-full bg-green-100">
                      <EditIcon
                        className="h-6 w-6 text-green-600"
                        aria-hidden="true"
                      />
                    </div>
                    <div className="mt-3 text-center sm:mt-5">
                      <Dialog.Title
                        as="h3"
                        className="text-lg font-medium leading-6 text-gray-900"
                      >
                        Edit Reservation
                      </Dialog.Title>

                      <div className="text-red-600">{responseC?.message}</div>
                      <div className="mt-2 w-full flex justify-center items-center flex-col">
                        <div
                          className="bg-white border border-gray-200 rounded-lg"
                          data-hs-input-number=""
                        >
                          <div className="w-full flex justify-between items-center gap-x-1">
                            <div className="grow py-2 px-3">
                              <span className="block text-xs text-gray-500 dark:text-neutral-400">
                                Select quantity
                              </span>
                              <input
                                className="w-full p-0 bg-transparent border-0 text-gray-800 focus:ring-0"
                                type="text"
                                value={count.toString()}
                                data-hs-input-number-input=""
                              />
                            </div>
                            <div className="flex flex-col -gap-y-px divide-y divide-gray-200 border-s border-gray-200">
                              <button
                                type="button"
                                onClick={() => {
                                  setCount(count - 1);
                                }}
                                className="size-7 inline-flex justify-center items-center gap-x-2 text-sm font-medium rounded-se-lg bg-gray-50 text-gray-800 hover:bg-gray-100 disabled:opacity-50 disabled:pointer-events-none "
                                data-hs-input-number-decrement=""
                              >
                                <svg
                                  className="flex-shrink-0 size-3.5"
                                  xmlns="http://www.w3.org/2000/svg"
                                  width="24"
                                  height="24"
                                  viewBox="0 0 24 24"
                                  fill="none"
                                  stroke="currentColor"
                                  strokeWidth="2"
                                  strokeLinecap="round"
                                  strokeLinejoin="round"
                                >
                                  <path d="M5 12h14"></path>
                                </svg>
                              </button>
                              <button
                                type="button"
                                onClick={() => {
                                  setCount(count + 1);
                                }}
                                className="size-7 inline-flex justify-center items-center gap-x-2 text-sm font-medium rounded-ee-lg bg-gray-50 text-gray-800 hover:bg-gray-100 disabled:opacity-50 disabled:pointer-events-none"
                                data-hs-input-number-increment=""
                              >
                                <svg
                                  className="flex-shrink-0 size-3.5"
                                  xmlns="http://www.w3.org/2000/svg"
                                  width="24"
                                  height="24"
                                  viewBox="0 0 24 24"
                                  fill="none"
                                  stroke="currentColor"
                                  strokeWidth="2"
                                  strokeLinecap="round"
                                  strokeLinejoin="round"
                                >
                                  <path d="M5 12h14"></path>
                                  <path d="M12 5v14"></path>
                                </svg>
                              </button>
                            </div>
                          </div>
                        </div>

                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                          <DemoContainer
                            sx={{ paddingTop: 4 }}
                            components={["DateTimePicker"]}
                          >
                            <DateTimePicker
                              sx={{ width: 180 }}
                              onChange={(newValue) => {
                                setStart(newValue); // Log the selected date/time value
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
                                setEnd(newValue); // Log the selected date/time value
                              }}
                              label="Reservation Ending Time"
                            />
                          </DemoContainer>
                        </LocalizationProvider>
                      </div>
                    </div>
                  </div>
                  <div className="mt-5 sm:mt-6 sm:grid sm:grid-flow-row-dense sm:grid-cols-2 sm:gap-3">
                    <button
                      type="button"
                      className="inline-flex w-full justify-center rounded-md border border-transparent bg-indigo-600 px-4 py-2 text-base font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 sm:col-start-2 sm:text-sm"
                      onClick={() => editReservation()}
                    >
                      Edit
                    </button>
                    <button
                      type="button"
                      className="mt-3 inline-flex w-full justify-center rounded-md border border-gray-300 bg-white px-4 py-2 text-base font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 sm:col-start-1 sm:mt-0 sm:text-sm"
                      onClick={() => setActive(false)}
                      ref={cancelButtonRef}
                    >
                      Cancel
                    </button>
                  </div>
                </Dialog.Panel>
              </Transition.Child>
            </div>
          </div>
        </Dialog>
      </Transition.Root>
    </>
  );
};
