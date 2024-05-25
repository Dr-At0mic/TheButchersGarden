import {
  Bars4Icon,
  Squares2X2Icon as Squares2X2IconMini,
} from "@heroicons/react/20/solid";
import { AvailableTableData } from "../../systemutils/models/Models";
import couples from "../../../src/assets/couples.jpg";
import { ReservationSideBar } from "./ReservationSideBar.tsx";
import { useState } from "react";

function classNames(...classes: (string | undefined | null | false)[]): string {
  return classes.filter(Boolean).join(" ");
}

interface AvailableTablesProps {
  availableTables: AvailableTableData[];
}

export const AvailableTables: React.FC<AvailableTablesProps> = ({
  availableTables,
}) => {
  const [index, setIndex] = useState<number>(0);
  return (
    <>
      <div className="flex flex-1 items-stretch overflow-hidden">
        <main className="flex-1 overflow-y-auto">
          <div className="mx-auto max-w-7xl px-4 pt-8 sm:px-6 lg:px-8">
            <div className="flex">
              <h1 className="flex-1 text-2xl font-bold text-gray-900">
                Tables
              </h1>
              <div className="ml-6 flex items-center rounded-lg bg-gray-100 p-0.5 sm:hidden">
                <button
                  type="button"
                  className="rounded-md p-1.5 text-gray-400 hover:bg-white hover:shadow-sm focus:outline-none focus:ring-2 focus:ring-inset focus:ring-indigo-500"
                >
                  <Bars4Icon className="h-5 w-5" aria-hidden="true" />
                  <span className="sr-only">Use list view</span>
                </button>
                <button
                  type="button"
                  className="ml-0.5 rounded-md bg-white p-1.5 text-gray-400 shadow-sm focus:outline-none focus:ring-2 focus:ring-inset focus:ring-indigo-500"
                >
                  <Squares2X2IconMini className="h-5 w-5" aria-hidden="true" />
                  <span className="sr-only">Use grid view</span>
                </button>
              </div>
            </div>

            <section className="mt-8 pb-16" aria-labelledby="gallery-heading">
              <h2 id="gallery-heading" className="sr-only">
                Recently viewed
              </h2>
              <ul
                role="list"
                className="grid grid-cols-2 gap-x-4 gap-y-8 sm:grid-cols-3 sm:gap-x-6 md:grid-cols-4 lg:grid-cols-3 xl:grid-cols-4 xl:gap-x-8"
              >
                {availableTables.length > 0 &&
                  availableTables.map((table, i) => (
                    <li
                      key={table.tableId}
                      className="relative"
                      onClick={() => {
                        setIndex(i);
                      }}
                    >
                      <div
                        className={classNames(
                          table
                            ? "ring-2 ring-offset-2 ring-indigo-500"
                            : "focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-offset-gray-100 focus-within:ring-indigo-500",
                          "group block w-full aspect-w-10 aspect-h-7 rounded-lg bg-gray-100 overflow-hidden"
                        )}
                      >
                        <img
                          src={couples}
                          alt=""
                          className={classNames(
                            table ? "" : "group-hover:opacity-75",
                            "object-cover pointer-events-none"
                          )}
                        />
                        <button
                          type="button"
                          className="absolute inset-0 focus:outline-none"
                        >
                          <span className="sr-only">
                            View details for {table.tableType.tableTypeName}
                          </span>
                        </button>
                      </div>
                      <p className="pointer-events-none mt-2 block truncate text-sm font-medium text-gray-900">
                        {table.tableType.tableTypeName}
                      </p>
                      <p className="pointer-events-none block text-sm font-medium text-gray-500">
                        {table.tableType.seats}
                      </p>
                    </li>
                  ))}
              </ul>
            </section>
          </div>
        </main>
        <ReservationSideBar availableTables={availableTables[index]} />
      </div>
    </>
  );
};
