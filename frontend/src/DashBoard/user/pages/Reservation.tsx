import { AvailableTables } from "../../shared/AvailableTables.tsx";
import { useEffect, useState } from "react";
import axios from "axios";
import { UseAuth } from "../../shared/UseAuth.tsx";
import { AvailableTableData } from "../../../systemutils/models/Models";
import { NoContent } from "./NoContent.tsx";
import PageLoader from "../../../components/common/loaders/PageLoader.tsx";

export default function Reservation() {
  const [availableTables, setAvailableTables] = useState<AvailableTableData[]>(
    []
  );
  const [loader, setLoader] = useState<boolean>(true);
  useEffect(() => {
    console.log(UseAuth());
    const getAvailableTables = async () => {
      setLoader(true);
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
          console.log(availableTables);
          console.log(response);
        })
        .catch((err) => {
          console.log(err);
        });
      setLoader(false);
    };

    getAvailableTables();
  }, []);

  return (
    <>
      <div className="flex h-full">
        <div className="flex flex-1 flex-col overflow-hidden">
          {loader ? (
            <>
              <PageLoader />
            </>
          ) : (
            <>
              {availableTables && availableTables.length > 0 ? (
                <>
                  <AvailableTables availableTables={availableTables} />
                </>
              ) : (
                <NoContent />
              )}
            </>
          )}
        </div>
      </div>
    </>
  );
}
