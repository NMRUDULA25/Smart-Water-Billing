import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import {
    getAllMeterReadings,
    createMeterReading,
    deleteMeterReading
} from "../services/meterReadingService";

function MeterReadings() {

    const [readings, setReadings] = useState([]);
    const [search, setSearch] = useState("");

    const [formData, setFormData] = useState({
        consumerNumber: "",
        previousReading: "",
        currentReading: "",
        unitsConsumed: "",
        readingDate: ""
    });

    useEffect(() => {
        loadReadings();
    }, []);

    const loadReadings = async () => {
        try {
            const response = await getAllMeterReadings();

            response.data.sort(
                (a, b) => new Date(b.readingDate) - new Date(a.readingDate)
            );

            setReadings(response.data);

        } catch (error) {
            console.log(error);
        }
    };

    const handleChange = (e) => {

        const { name, value } = e.target;

        let updatedData = {
            ...formData,
            [name]: value
        };

        if (
            (name === "previousReading" || name === "currentReading") &&
            updatedData.previousReading !== "" &&
            updatedData.currentReading !== ""
        ) {

            updatedData.unitsConsumed =
                Number(updatedData.currentReading) -
                Number(updatedData.previousReading);

        }

        setFormData(updatedData);

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        if (
            Number(formData.currentReading) <
            Number(formData.previousReading)
        ) {
            alert("Current Reading cannot be less than Previous Reading.");
            return;
        }

        try {

            await createMeterReading(formData);

            alert("Meter Reading Saved Successfully");

            setFormData({
                consumerNumber: "",
                previousReading: "",
                currentReading: "",
                unitsConsumed: "",
                readingDate: ""
            });

            loadReadings();

        } catch (error) {

            console.log(error);
            alert("Unable to save meter reading.");

        }

    };

    const handleDelete = async (id) => {

        if (!window.confirm("Delete this Meter Reading?")) return;

        try {

            await deleteMeterReading(id);
            loadReadings();

        } catch (error) {

            console.log(error);

        }

    };

    return (

        <Layout>

            <h2 className="mb-4">Meter Readings</h2>

            <div className="card mb-4">

                <div className="card-header bg-success text-white">
                    Add Meter Reading
                </div>

                <div className="card-body">

                    <form onSubmit={handleSubmit}>

                        <div className="row">

                            <div className="col-md-3 mb-3">

                                <input
                                    className="form-control"
                                    placeholder="Consumer Number"
                                    name="consumerNumber"
                                    value={formData.consumerNumber}
                                    onChange={handleChange}
                                    required
                                />

                            </div>

                            <div className="col-md-3 mb-3">

                                <input
                                    type="number"
                                    className="form-control"
                                    placeholder="Previous Reading"
                                    name="previousReading"
                                    value={formData.previousReading}
                                    onChange={handleChange}
                                    required
                                />

                            </div>

                            <div className="col-md-3 mb-3">

                                <input
                                    type="number"
                                    className="form-control"
                                    placeholder="Current Reading"
                                    name="currentReading"
                                    value={formData.currentReading}
                                    onChange={handleChange}
                                    required
                                />

                            </div>

                            <div className="col-md-3 mb-3">

                                <input
                                    type="number"
                                    className="form-control"
                                    placeholder="Units Consumed"
                                    value={formData.unitsConsumed}
                                    readOnly
                                />

                            </div>

                            <div className="col-md-4 mb-3">

                                <input
                                    type="date"
                                    className="form-control"
                                    name="readingDate"
                                    value={formData.readingDate}
                                    onChange={handleChange}
                                    required
                                />

                            </div>

                            <div className="col-md-2 mb-3">

                                <button
                                    className="btn btn-success w-100"
                                >
                                    Save
                                </button>

                            </div>

                        </div>

                    </form>

                </div>

            </div>

            <div className="row mb-3">

                <div className="col-md-5">

                    <input
                        type="text"
                        className="form-control"
                        placeholder="🔍 Search Consumer Number"
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                    />

                </div>

            </div>

            <table className="table table-bordered table-striped">

                <thead className="table-dark">

                <tr>

                    <th>ID</th>
                    <th>Consumer</th>
                    <th>Previous</th>
                    <th>Current</th>
                    <th>Units</th>
                    <th>Date</th>
                    <th>Action</th>

                </tr>

                </thead>

                <tbody>

                {readings
                    .filter((reading) =>
                        reading.consumerNumber
                            .toLowerCase()
                            .includes(search.toLowerCase())
                    )
                    .map((reading) => (

                        <tr key={reading.id}>

                            <td>{reading.id}</td>
                            <td>{reading.consumerNumber}</td>
                            <td>{reading.previousReading}</td>
                            <td>{reading.currentReading}</td>
                            <td>{reading.unitsConsumed}</td>
                            <td>{reading.readingDate}</td>

                            <td>

                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() => handleDelete(reading.id)}
                                >
                                    Delete
                                </button>

                            </td>

                        </tr>

                    ))}

                {readings.filter((reading) =>
                    reading.consumerNumber
                        .toLowerCase()
                        .includes(search.toLowerCase())
                ).length === 0 && (

                    <tr>

                        <td colSpan="7" className="text-center">
                            No Meter Readings Found
                        </td>

                    </tr>

                )}

                </tbody>

            </table>

        </Layout>

    );

}

export default MeterReadings;