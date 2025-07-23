
export const generateSlug = (id: string, name: string): string => {
  const slugName = name.toLowerCase().replace(/[^a-z0-9]+/g, '-').replace(/^-|-$/g, '');
  return `${id}-${slugName}`;
};

export const extractIdFromSlug = (slug: string): string => {
  const id = slug.split('-')[0];
  return id;
};
