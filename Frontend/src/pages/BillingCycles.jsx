import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import {
    getAllBillingCycles,
    createBillingCycle,
    deleteBillingCycle,
    openBillingCycle,
    finalizeBillingCycle,
    archiveBillingCycle
} from "../services/billingCycleService";

function BillingCycles() {

    const [cycles, setCycles] = useState([]);

    const [formData, setFormData] = useState({
        cycleName: "",
        startDate: "",
        endDate: "",
        status: "OPEN"
    });

    useEffect(() => {
        loadCycles();
    }, []);

    const loadCycles = async () => {
        try {
            const response = await getAllBillingCycles();
            setCycles(response.data);
        } catch (error) {
            console.log(error);
        }
    };

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            await createBillingCycle(formData);

            alert("Billing Cycle Created Successfully");

            setFormData({
                cycleName: "",
                startDate: "",
                endDate: "",
                status: "OPEN"
            });

            loadCycles();

        } catch (error) {

            console.log(error);
            alert("Unable to create Billing Cycle");

        }

    };

    const handleDelete = async (id) => {

        if (window.confirm("Delete this Billing Cycle?")) {

            await deleteBillingCycle(id);

            loadCycles();

        }

    };

    const handleOpen = async (id) => {

        await openBillingCycle(id);

        loadCycles();

    };

    const handleFinalize = async (id) => {

        await finalizeBillingCycle(id);

        loadCycles();

    };

    const handleArchive = async (id) => {

        await archiveBillingCycle(id);

        loadCycles();

    };

    return (

        <Layout>

            <h2 className="mb-4">
                Billing Cycles
            </h2>

            <form onSubmit={handleSubmit}>

                <div className="row mb-3">

                    <div className="col">

                        <input
                            className="form-control"
                            placeholder="Cycle Name"
                            name="cycleName"
                            value={formData.cycleName}
                            onChange={handleChange}
                            required
                        />

                    </div>

                    <div className="col">

                        <input
                            type="date"
                            className="form-control"
                            name="startDate"
                            value={formData.startDate}
                            onChange={handleChange}
                            required
                        />

                    </div>

                    <div className="col">

                        <input
                            type="date"
                            className="form-control"
                            name="endDate"
                            value={formData.endDate}
                            onChange={handleChange}
                            required
                        />

                    </div>

                    <div className="col">

                        <button className="btn btn-primary w-100">
                            Create
                        </button>

                    </div>

                </div>

            </form>

            <hr/>

            <table className="table table-bordered table-striped">

                <thead className="table-dark">

                <tr>

                    <th>ID</th>
                    <th>Cycle</th>
                    <th>Start</th>
                    <th>End</th>
                    <th>Status</th>
                    <th>Actions</th>

                </tr>

                </thead>

                <tbody>

                {

                    cycles.map((cycle)=>(

                        <tr key={cycle.id}>

                            <td>{cycle.id}</td>
                            <td>{cycle.cycleName}</td>
                            <td>{cycle.startDate}</td>
                            <td>{cycle.endDate}</td>
                            <td>{cycle.status}</td>

                            <td>

                                <button
                                    className="btn btn-success btn-sm me-2"
                                    onClick={()=>handleOpen(cycle.id)}
                                >
                                    Open
                                </button>

                                <button
                                    className="btn btn-warning btn-sm me-2"
                                    onClick={()=>handleFinalize(cycle.id)}
                                >
                                    Finalize
                                </button>

                                <button
                                    className="btn btn-secondary btn-sm me-2"
                                    onClick={()=>handleArchive(cycle.id)}
                                >
                                    Archive
                                </button>

                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={()=>handleDelete(cycle.id)}
                                >
                                    Delete
                                </button>

                            </td>

                        </tr>

                    ))

                }

                </tbody>

            </table>

        </Layout>

    );

}

export default BillingCycles;