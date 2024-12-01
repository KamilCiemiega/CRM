import { clientCompanyImages } from "./clientCompanyImages";
import { Client } from "../../../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import { Company } from "../../../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";

type InitializeDataProps = {
    clientCompanydata: Client[];
    typeOfView: 'clients' | 'companies';
    searchValue: string;
}

type FiltredDataProps = {
    clients: ExpandedClient[]; 
    companies: ExpandedCompany[];
    typeOfView: 'clients' | 'companies';
    searchValue: string;
}

export type ExpandedCompany = Company & { image: string };
export type ExpandedClient = Client & { nameSurname: string, image: string}


export const initializeData = ({clientCompanydata, typeOfView, searchValue}: InitializeDataProps) => {
    const images = clientCompanyImages[0].clientImage.map(img => Object.values(img)[0]);

    const clientsData = clientCompanydata.map((c, index) => {
        const image = images[index % images.length];
        return {...c, nameSurname: `${c.name} ${c.surname}`, image};
    });

    const imagePath = Object.values(clientCompanyImages[0].companyImage[0])[0];
    const companiesData = clientCompanydata
        .map(client => {
            if (!client.company) return null;
            return { ...client.company, image: imagePath };
        })
        .filter((entry): entry is ExpandedCompany => entry !== null);

        console.log(companiesData)

    const filtredView = filtredData({ clients: clientsData, companies: companiesData, typeOfView, searchValue });

    return { filtredView, clientsData, companiesData}
};

const filtredData = ({clients, companies, typeOfView, searchValue }: FiltredDataProps) => {
    const filteredViewType = typeOfView === 'clients'
    ? clients.filter(client =>
        client.name.toLowerCase().includes(searchValue.toLowerCase())
    )
    : companies.filter(company =>
        company.name.toLowerCase().includes(searchValue.toLowerCase())
    );

    return filteredViewType;
}