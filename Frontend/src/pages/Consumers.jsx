import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import {
    getAllConsumers,
    createConsumer,
    deleteConsumer,
} from "../services/consumerService";

function Consumers() {

    const [consumers, setConsumers] = useState([]);
    const [search, setSearch] = useState("");

    const [consumer, setConsumer] = useState({
        consumerNumber: "",
        ownerName: "",
        address: "",
        phone: "",
        connectionType: ""
    });

    useEffect(() => {
        loadConsumers();
    }, []);

    const loadConsumers = async () => {
        try {
            const response = await getAllConsumers();
            setConsumers(response.data);
        } catch (error) {
            console.error(error);
        }
    };

    const handleChange = (e) => {
        setConsumer({
            ...consumer,
            [e.target.name]: e.target.value
        });
    };

    const saveConsumer = async (e) => {

        e.preventDefault();

        try {

            await createConsumer(consumer);

            alert("Consumer Added Successfully");

            setConsumer({
                consumerNumber: "",
                ownerName: "",
                address: "",
                phone: "",
                connectionType: ""
            });

            loadConsumers();

        } catch (error) {

            console.error(error);
            alert("Unable to Save Consumer");

        }

    };

    const removeConsumer = async (id) => {

        if (!window.confirm("Delete Consumer?")) return;

        try {

            await deleteConsumer(id);

            alert("Consumer Deleted Successfully");

            loadConsumers();

        } catch (error) {

            console.error(error);
            alert("Unable to Delete Consumer");

        }

    };

    return (

        <Layout>

            <h2 className="mb-4">Consumer Management</h2>

            {/* Add Consumer */}

            <div className="card mb-4">

                <div className="card-header bg-primary text-white">
                    Add Consumer
                </div>

                <div className="card-body">

                    <form onSubmit={saveConsumer} autoComplete="off">

                        <div className="row">

                            <div className="col-md-4 mb-3">

                                <input
                                    className="form-control"
                                    placeholder="Consumer Number"
                                    name="consumerNumber"
                                    value={consumer.consumerNumber}
                                    onChange={handleChange}
                                    required
                                />

                            </div>

                            <div className="col-md-4 mb-3">

                                <input
                                    className="form-control"
                                    placeholder="Owner Name"
                                    name="ownerName"
                                    value={consumer.ownerName}
                                    onChange={handleChange}
                                    required
                                />

                            </div>

                            <div className="col-md-4 mb-3">

                                <input
                                    className="form-control"
                                    placeholder="Phone"
                                    name="phone"
                                    value={consumer.phone}
                                    onChange={handleChange}
                                    required
                                />

                            </div>

                            <div className="col-md-6 mb-3">

                                <input
                                    className="form-control"
                                    placeholder="Address"
                                    name="address"
                                    value={consumer.address}
                                    onChange={handleChange}
                                    required
                                />

                            </div>

                            <div className="col-md-4 mb-3">

                                <select
                                    className="form-select"
                                    name="connectionType"
                                    value={consumer.connectionType}
                                    onChange={handleChange}
                                    required
                                >
                                    <option value="">Select Type</option>
                                    <option value="Domestic">Domestic</option>
                                    <option value="Commercial">Commercial</option>
                                </select>

                            </div>

                            <div className="col-md-2 mb-3">

                                <button
                                    type="submit"
                                    className="btn btn-primary w-100"
                                >
                                    Save
                                </button>

                            </div>

                        </div>

                    </form>

                </div>

            </div>

            {/* Search */}

            <div className="row mb-3">

                <div className="col-md-5">

                    <input
                        type="text"
                        className="form-control"
                        placeholder="🔍 Search by Consumer Number or Owner Name"
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                    />

                </div>

            </div>

            {/* Consumer Table */}

            <table className="table table-bordered table-striped">

                <thead className="table-dark">

                <tr>

                    <th>ID</th>
                    <th>Consumer No</th>
                    <th>Owner</th>
                    <th>Phone</th>
                    <th>Connection</th>
                    <th>Action</th>

                </tr>

                </thead>

                <tbody>

                {consumers
                    .filter((c) =>

                        c.consumerNumber
                            .toLowerCase()
                            .includes(search.toLowerCase())

                        ||

                        c.ownerName
                            .toLowerCase()
                            .includes(search.toLowerCase())

                    )
                    .map((c) => (

                        <tr key={c.id}>

                            <td>{c.id}</td>
                            <td>{c.consumerNumber}</td>
                            <td>{c.ownerName}</td>
                            <td>{c.phone}</td>
                            <td>{c.connectionType}</td>

                            <td>

                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() => removeConsumer(c.id)}
                                >
                                    Delete
                                </button>

                            </td>

                        </tr>

                    ))}

                {consumers.filter((c) =>
                    c.consumerNumber.toLowerCase().includes(search.toLowerCase()) ||
                    c.ownerName.toLowerCase().includes(search.toLowerCase())
                ).length === 0 && (

                    <tr>

                        <td colSpan="6" className="text-center">

                            No Consumers Found

                        </td>

                    </tr>

                )}

                </tbody>

            </table>

        </Layout>

    );

}

export default Consumers;