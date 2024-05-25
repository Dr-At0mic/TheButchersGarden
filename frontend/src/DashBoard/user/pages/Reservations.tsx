import couple from "../../../assets/couples.jpg";
import { useContext, useEffect, useState } from "react";
import { UseAuth } from "../../shared/UseAuth.tsx";
import axios from "axios";
import { AvailableTableData } from "../../../systemutils/models/Models";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import { EditReservation } from "../components/EditReservation.tsx";
import { Warning } from "../../../components/common/PopUs/Warning.tsx";
const people = [
  {
    name: "Couple",
    title: "2",
    role: "Member",
    image: couple,
  },
  // More people...
];

export const Reservations = () => {
  const [response, setResponse] = useState<AvailableTableData[]>();
  const [loading, setLoading] = useState<boolean>(true);
  const [active, setActive] = useState<boolean>(false);
  const [itemdelete, setItemdelete] = useState<boolean>(false);

  useEffect(() => {
    const getReservedTables = async () => {
      setLoading(true);
      await axios
        .get(
          `${import.meta.env.VITE_GATEWAY_DOMAIN}reservation/reservedTables`,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${UseAuth()}`,
            },
            withCredentials: true,
          }
        )
        .then((response) => {
          const ServerResponse = response.data.data;
          setResponse(ServerResponse);
          console.log(response);
        })
        .catch((err) => {
          console.log(err);
        });
    };
    getReservedTables();
    setLoading(false);
  }, []);

  return (
    <>
      <div className="px-4 sm:px-6 lg:px-8">
        <div className="sm:flex sm:items-center">
          <div className="sm:flex-auto">
            <h1 className="text-xl font-semibold text-gray-900">
              Active Reservations
            </h1>
            <p className="mt-2 text-sm text-gray-700">
              A list of all the Active Reservation in your account.
            </p>
          </div>
          <div className="mt-4 sm:mt-0 sm:ml-16 sm:flex-none">
            <button
              type="button"
              className="inline-flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-4 py-2 text-sm font-medium text-white shadow-sm bg-yellow-600 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 sm:w-auto"
            >
              View History
            </button>
          </div>
        </div>
        <div className="mt-8 flex flex-col">
          <div className="-my-2 -mx-4 overflow-x-auto sm:-mx-6 lg:-mx-8">
            <div className="inline-block min-w-full py-2 align-middle md:px-6 lg:px-8">
              <div className="overflow-hidden shadow ring-1 ring-black ring-opacity-5 md:rounded-lg">
                <table className="min-w-full divide-y divide-gray-300">
                  <thead className="bg-gray-50">
                    <tr>
                      <th
                        scope="col"
                        className="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-6"
                      >
                        Table Type
                      </th>
                      <th
                        scope="col"
                        className="px-3 py-3.5 text-left text-sm font-semibold text-gray-900"
                      >
                        Price
                      </th>
                      <th
                        scope="col"
                        className="px-3 py-3.5 text-left text-sm font-semibold text-gray-900"
                      >
                        Status
                      </th>
                      <th
                        scope="col"
                        className="px-3 py-3.5 text-left text-sm font-semibold text-gray-900"
                      >
                        Table Count
                      </th>
                      <th
                        scope="col"
                        className="px-3 py-3.5 text-left text-sm font-semibold text-gray-900"
                      >
                        Reservation Starts
                      </th>
                      <th
                        scope="col"
                        className="px-1 py-2 text-left text-sm font-semibold text-gray-900"
                      >
                        Reservation Ends
                      </th>
                      <th
                        scope="col"
                        className="relative py-3.5 pl-3 pr-4 sm:pr-6"
                      >
                        <span className="sr-only">Edit</span>
                      </th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200 bg-white">
                    {response &&
                      response.map(
                        (responsetable: AvailableTableData, index) => (
                          <>
                            <tr key={index}>
                              <td className="whitespace-nowrap py-4 pl-4 pr-3 text-sm sm:pl-6">
                                <div className="flex items-center">
                                  <div className="h-10 w-10 flex-shrink-0">
                                    <img
                                      className="h-10 w-10 rounded-lg"
                                      src={couple}
                                      alt=""
                                    />
                                  </div>
                                  <div className="ml-4">
                                    <div className="font-medium text-gray-900">
                                      {responsetable.tableType.tableTypeName}
                                    </div>
                                  </div>
                                </div>
                              </td>
                              <td className="whitespace-nowrap px-3 py-4 text-sm text-gray-500">
                                <div className="text-gray-900">
                                  {responsetable.tablePrice}
                                </div>
                              </td>
                              <td className="whitespace-nowrap px-3 py-4 text-sm text-gray-500">
                                <span className="inline-flex rounded-full bg-green-100 px-2 text-xs font-semibold leading-5 text-green-800">
                                  Active
                                </span>
                              </td>
                              <td className="whitespace-nowrap px-3 py-4 text-sm text-gray-500">
                                {responsetable.tableCount.toString()}
                              </td>
                              <td className="whitespace-nowrap px-3 py-4 text-sm text-gray-500">
                                {responsetable.bookingStarts.toString()}
                              </td>
                              <td className="whitespace-nowrap px-3 py-4 text-sm text-gray-500">
                                {responsetable.bookingEnds.toString()}
                              </td>

                              <td className="relative whitespace-nowrap space-x-4 py-4 pl-3 pr-4 text-right text-sm font-medium sm:pr-6">
                                <span
                                  onClick={() => {
                                    setActive(true);
                                  }}
                                  className="text-indigo-600 cursor-pointer hover:text-indigo-900"
                                >
                                  <EditIcon />
                                </span>
                                <span
                                  onClick={() => {
                                    setItemdelete(true);
                                  }}
                                  className="text-red-700 cursor-pointer hover:text-red-600"
                                >
                                  <DeleteIcon />
                                </span>
                              </td>
                            </tr>
                            <EditReservation
                              key={index}
                              active={active}
                              setActive={setActive}
                              resId={responsetable.resId}
                              tableCount={responsetable.tableCount}
                              tableId={responsetable.tableId}
                            />
                            <Warning
                              key={index + 1}
                              itemdelete={itemdelete}
                              setItemdelete={setItemdelete}
                              res_Id={responsetable.resId}
                            />
                          </>
                        )
                      )}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
