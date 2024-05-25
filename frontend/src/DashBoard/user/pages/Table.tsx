import { useEffect, useState } from "react";
import axios from "axios";
import { UseAuth } from "../../shared/UseAuth.tsx";
export const Table = () => {
  interface ReservTable {
    tableId: number;
    isAc: boolean;
    tableCount: number;
    tablePrice: string;
    tableType: tableType;
  }
  interface tableType {
    tableTypeId: number;
    tableTypeName: string;
    seats: number;
  }

  const [availableTables, setAvailableTables] = useState<ReservTable[]>([]);

  useEffect(() => {
    const getAvailableTables = async () => {
      await axios
        .get(
          `${import.meta.env.VITE_GATEWAY_DOMAIN}reservation/availableTables`,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${UseAuth()}`,
            },
            withCredentials: true,
          }
        )
        .then((response) => {
          setAvailableTables(response.data.data);
          console.log(response);
        })
        .catch((err) => {
          console.log(err);
        });
    };

    getAvailableTables();
  }, []);

  return (
    <div className="flex flex-col w-screen min-h-screen bg-transparent pe-7">
      {availableTables.length > 0 ? (
        <>
          <p className="text-2xl useinter ps-5 pt-5 text-black font-bold">
            Available Tables
          </p>
          <table className="min-w-full mt-5 border-collapse border bg-lime-100/50 border-gray-300">
            <thead>
              <tr>
                <th className="font-semibold border border-gray-300 px-4 py-2">
                  No
                </th>
                <th className="font-semibold border border-gray-300 px-4 py-2">
                  Name
                </th>
                <th className="font-semibold border border-gray-300 px-4 py-2">
                  Price
                </th>
                <th className="font-semibold border border-gray-300 px-4 py-2">
                  Count
                </th>
                <th className="font-semibold border border-gray-300 px-4 py-2">
                  AC
                </th>
                <th className="font-semibold border border-gray-300 px-4 py-2">
                  Seats
                </th>
              </tr>
            </thead>
            <tbody>
              {availableTables.map((table, index) => (
                <tr key={table.tableId}>
                  <td className="border border-gray-300 px-4 py-2 text-center">
                    {index + 1}
                  </td>
                  <td className="border border-gray-300 px-4 py-2 text-center">
                    {availableTables[index].tableType.tableTypeName}
                  </td>
                  <td className="border border-gray-300 px-4 py-2 text-center">
                    {availableTables[index].tablePrice}
                  </td>
                  <td className="border border-gray-300 px-4 py-2 text-center">
                    {availableTables[index].tableCount}
                  </td>
                  <td className="border border-gray-300 px-4 py-2 text-center">
                    {availableTables[index].isAc}
                  </td>
                  <td className="border border-gray-300 px-4 py-2 text-center">
                    {availableTables[index].tableType.seats}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </>
      ) : (
        <p className="text-2xl useinter ps-5 pt-5 text-black font-bold">
          No available tables
        </p>
      )}
    </div>
  );
};
