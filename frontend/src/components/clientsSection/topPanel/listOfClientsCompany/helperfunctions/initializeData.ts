import { clientCompanyImages } from "./clientCompanyImages";
import { Client, Company } from "../../../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";

type InitializeDataProps = {
    clientData: Client[];
    companyData: Company[];
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


export const initializeData = ({clientData, companyData, typeOfView, searchValue}: InitializeDataProps) => {
    const images = clientCompanyImages[0].clientImage.map(img => Object.values(img)[0]);

    const clientsData = clientData.map((c, index) => {
        const image = images[index % images.length];
        return {...c, nameSurname: `${c.name} ${c.surname}`, image};
    });

    const imagePath = Object.values(clientCompanyImages[0].companyImage[0])[0];
    const companiesData = companyData
    .map(company => {
        return { ...company, image: imagePath };
    })

    const filtredView = filtredData({ clients: clientsData, companies: companiesData, typeOfView, searchValue });

    return { filtredView, clientsData, companiesData}
};

const filtredData = ({clients, companies, typeOfView, searchValue }: FiltredDataProps) => {
    const filteredViewType = typeOfView === 'clients'
    ? clients.filter(client =>
        client.nameSurname.toLowerCase().includes(searchValue.toLowerCase())
    )
    : companies.filter(company =>
        company.name.toLowerCase().includes(searchValue.toLowerCase())
    );
    return filteredViewType;
}