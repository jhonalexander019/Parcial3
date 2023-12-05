import { Client, Variables, logger } from "camunda-external-task-client-js"

// configuration for the Client:
//  - 'baseUrl': url to the Process Engine
//  - 'logger': utility to automatically log important events
const config = {baseUrl: "http://localhost:8080/engine-rest", use: logger};

// create a client instance with custom configuration
const client = new Client(config);

client.subscribe("codeAssignment", async function({ task, taskService }) {
    const Nombre = task.variables.get("Nombre");
    const Cedula = task.variables.get("Cedula");

    const CodigoEspecial = Nombre.replace(/\s+/g, '')+Cedula;
    const processVariables = new Variables();
    processVariables.set("DIANCode", CodigoEspecial);

    await taskService.complete(task, processVariables);
});
