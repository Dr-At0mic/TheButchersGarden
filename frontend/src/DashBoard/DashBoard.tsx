import { Route, Routes } from "react-router-dom";
import DashBoardLayout from "./layout/DashBoardLayout";
import NotFound from "../components/common/NotFound";
import SetTitle from "../components/common/SetTitle";
import Reservation from "./user/pages/Reservation.tsx";
import { Table } from "./user/pages/Table.tsx";
import { Reservations } from "./user/pages/Reservations.tsx";

function DashBoard() {
  return (
    <Routes>
      <Route path="/" element={<DashBoardLayout />}>
        <Route path="Reservation" element={<Reservation />} />
        <Route path="tables" element={<Table />} />
        <Route path="bookingChart" element={<Reservations />} />
      </Route>
      <Route
        path="*"
        element={
          <>
            <SetTitle title="You are Lost (404)" />
            <NotFound />
          </>
        }
      />
    </Routes>
  );
}

export default DashBoard;
